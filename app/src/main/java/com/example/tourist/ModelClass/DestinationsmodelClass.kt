package com.example.tourist.ModelClass

class DestinationsmodelClass {
    lateinit var name: String
    lateinit var image: String
    lateinit var details: String

    constructor(image: String, name: String, details: String) {
        this.name = name
        this.details = details
        this.image = image
    }

    constructor() {

    }
}