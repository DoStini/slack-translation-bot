package translation

import com.deepl.api.Translator

class DeepLTranslator (
    private val key : String
) : ITranslator {

    private val translator = Translator(key)
    private val languages = listOf(
        Language("pt-PT", "Portuguese", "flag-pt"),
        Language("en-GB", "English", "flag-england"),
        Language("es", "Spanish", "es"),
    )

    override fun translate(language: String, text: String): String {
        val result = translator.translateText(text, null, language)
        return result.text
    }

    override fun detect(text: String): Language {
        TODO("Not yet implemented")
    }

    override fun languages(): List<Language> {
        return languages
    }

    override fun languageByEmoji(reaction: String): Language? {
        return languages.find {
            it.emoji == reaction
        }
    }
}