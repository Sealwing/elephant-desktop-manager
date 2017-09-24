package sealwing.elephant.desktop.config

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object Config {

    val outputFolder: String

    init {
        val configFile = File("init.txt")
        this.outputFolder = if (configFile.exists()) {
            val reader = BufferedReader(FileReader(configFile))
            reader.readLine().split("=")[1]
        } else {
            "/Deals"
        }
    }
}