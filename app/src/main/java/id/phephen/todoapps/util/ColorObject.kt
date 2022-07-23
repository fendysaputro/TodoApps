package id.phephen.todoapps.util

/**
 * Created by Phephen on 24/07/2022.
 */
class ColorObject(var name: String, var hex: String, var contrastHex: String)
{
    val hexHash : String = "#$hex"
    val contrastHexHash : String = "#$contrastHex"
}