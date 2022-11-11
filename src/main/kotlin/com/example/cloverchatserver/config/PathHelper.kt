package com.example.cloverchatserver.config

class PathHelper {

    companion object {
        fun getChunks(dest: String): ArrayList<String> {
            val chunks = dest.split("/")

            return checkFirstIsEmpty(chunks)
        }

        private fun checkFirstIsEmpty(chunks: List<String>): ArrayList<String> {
            val list = ArrayList<String>()
            val iter = chunks.listIterator()

            if (iter.hasNext()) {
                list.add(iter.next())

                if (list[0].isEmpty()) {
                    list[0] = iter.next()
                }
            }

            while (iter.hasNext()) {
                list.add(iter.next())
            }

            return list
        }

        fun isMessageSession(chunks: List<String>): Boolean {
            if (chunks.size != 4) return false
            if (chunks[0] != "user") return false
            if (chunks[1] != "sub") return false
            if (chunks[2] != "message") return false

            return true
        }
    }
}