package g171.edalabtools.model

data class IndexedResult<T>(
    val result: T,
    val index: Int = 0
)
