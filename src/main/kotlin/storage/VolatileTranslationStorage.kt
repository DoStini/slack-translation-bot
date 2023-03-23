package storage

class VolatileTranslationStorage: TranslationStorage {
    val storage = HashMap<String, String>()
    override fun put(messageTs: String, text: String) {
        storage[messageTs] = text
    }

    override fun get(messageTs: String): String? {
        return storage[messageTs]
    }

    override fun remove(messageTs: String) {
     storage.remove(messageTs)
    }
}