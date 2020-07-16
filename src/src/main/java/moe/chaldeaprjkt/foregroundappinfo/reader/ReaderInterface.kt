package moe.chaldeaprjkt.foregroundappinfo.reader

internal interface ReaderInterface {
    fun read(timeToLife : Int = 3600): String
}
