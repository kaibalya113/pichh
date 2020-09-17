package com.kaibalya.picshh.model

import com.google.firebase.firestore.PropertyName

data class Post(
    @get:PropertyName("image_url") @set:PropertyName("image_url") var imageUrl : String ="",
    var description: String = "",
    @get:PropertyName("creation_time_ms") @set:PropertyName("creation_time_ms") var creationTimeMS: Long = 0,
    var user: User?= null
)