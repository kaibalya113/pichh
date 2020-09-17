package com.kaibalya.picshh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kaibalya.picshh.model.Post
import kotlinx.android.synthetic.main.activity_post.*

private const val TAG = "PostActivity"
class PostActivity : AppCompatActivity() {

    private lateinit var firestoreDb : FirebaseFirestore
    private lateinit var posts: MutableList<Post>
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        // Create the layout file which represents one post = Done
        // Create data source = Done
        posts = mutableListOf()
        // Create the Adapter
        adapter = PostAdapter(this, posts)
        // Bind the adapter and layout manager to Recycler View
        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(this)


        firestoreDb = FirebaseFirestore.getInstance()
        val postsname = firestoreDb.collection("posts").orderBy("creation_time_ms", Query.Direction.DESCENDING)
        postsname.addSnapshotListener { snapshot, firebaseFirestoreException ->
            if(snapshot == null || firebaseFirestoreException !=  null){
                Log.i(TAG, "Caught Exception while retriving", firebaseFirestoreException)
                return@addSnapshotListener
            }
            val postsDocs = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postsDocs)
            adapter.notifyDataSetChanged()
            for(document in postsDocs){
                Log.i(TAG, "Document: ${document.description}: ${document.imageUrl}")
            }
        }
        // retrive data from firestore
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_profile){
            // go to profile activity
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}