package g171.edalabtools.model

import g171.edalabtools.FE_PARSE_MESSAGE

class FormatException(message: String) : Exception(message) {
    constructor(parsing: String, path: String) : this(String.format(FE_PARSE_MESSAGE, parsing, path))
}