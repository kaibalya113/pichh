package com.kaibalya.picshh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kaibalya.picshh.model.Post
import com.kaibalya.picshh.model.User
import kotlinx.android.synthetic.main.activity_post.*

private const val TAG = "PostActivity"
public const val EXTRA_USERNAME ="EXTRA_USERNAME"
open class PostActivity : AppCompatActivity() {

    private var signediInUser: User? =null
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
        firestoreDb.collection("users").document(FirebaseAuth.getInstance()
            .currentUser?.uid as String).get()
            .addOnSuccessListener { userSnapshot ->
                signediInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, signediInUser.toString())
            }
            .addOnFailureListener{exception ->
                Log.i(TAG, exception.message)
            }


        var postsname = firestoreDb.collection("posts")
                                    .orderBy("creation_time_ms", Query.Direction.DESCENDING)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if(username != null){
            supportActionBar?.title = username
            postsname = postsname.whereEqualTo("user.name", username)
        }

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
        floatingbtn.setOnClickListener {
            var intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_profile){
            // go to profile activity
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, signediInUser?.name)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}