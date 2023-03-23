package storage

interface TranslationStorage {
    fun put(messageTs: String, text: String)
    fun get(messageTs: String) : String?
    fun remove(messageTs: String)
}
