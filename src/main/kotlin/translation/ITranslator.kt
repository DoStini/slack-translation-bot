package translation

interface ITranslator {
    fun translate(language: String, text: String) : String

    fun detect(text: String): Language

    fun languages() : List<Language>
    fun languageByEmoji(reaction: String): Language?
}
