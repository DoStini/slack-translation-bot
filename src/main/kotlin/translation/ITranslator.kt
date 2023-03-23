package translation

interface ITranslator {
    fun translate(language: Language, text: String) : String

    fun detect(text: String): Language

    fun languages() : List<Language>
}
