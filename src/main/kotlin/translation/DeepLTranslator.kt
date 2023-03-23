package translation

import com.deepl.api.Translator

class DeepLTranslator (
    private val key : String
) : ITranslator {

    private val translator = Translator(key)

    override fun translate(language: Language, text: String): String {
        val result = translator.translateText(text, null, language.value)
        return result.text
    }

    override fun detect(text: String): Language {
        TODO("Not yet implemented")
    }

    override fun languages(): List<Language> {
        return listOf(
            Language("pt-PT", "Portuguese", ":flag-pt:"),
            Language("en-GB", "English", ":flag-england:"),
            Language("es", "Spanish", ":es:"),
        )
    }
}