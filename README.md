# FTB-Quests translator

---

### Description

This utility allows you to read and translate blocks of text from the .snbt configuration file
of the [FTB-Quests](https://github.com/FTBTeam/FTB-Quests) mod into various languages using various APIs.

### Arguments

| Argument | Description                                                               | Default                             | Required |
|----------|---------------------------------------------------------------------------|-------------------------------------|----------|
| file     | The path to the original one .the snbt file                               | -                                   | ✅        |
| result   | The path before saving the translated file                                | -                                   | ❌        |
| source   | The language code of the source file text (ru, en, ar, zh...)             | en                                  | ❌        |
| target   | The language code of the target file text (ru, en, ar, zh...)             | ru                                  | ❌        |
| url      | URL of the text translation service API                                   | http://192.168.0.199:5000/translate | ❌        |
| key      | The API key of the text translation service                               | null                                | ❌        |
| type     | Types of text block for translation (description, title, subtitle, hover) | description, title, subtitle, hover | ❌        |


### Example

```console
java -jar FTBQuestsTranslator-0.1.jar \
--file=example.snbt \
--result=example.snbt \
--url=https://libretranslate.com/translate \
--key=47434aec-9031-428b-bed7-4ab5a44329f2
```