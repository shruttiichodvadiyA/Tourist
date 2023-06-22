package com.example.tourist.ModelClass

class ImagesModelClass {
    lateinit var name: String
    lateinit var image:String

    constructor(image:String,name:String){
        this.name = name
        this.image=image
    }
    constructor(){

    }
}