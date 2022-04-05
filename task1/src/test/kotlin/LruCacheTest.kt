import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class LruCacheTest {
    private var cache: LruCache<String, Int> = LruCache(4)

    @Before
    fun clearCache() {
        cache = LruCache(4)
    }

    @Test
    fun basicTest() {
        assertEquals(null, cache["any string"])
        cache.apply {
            put("a", 1)
            put("b", 2)
            put("c", 3)
            put("d", 4)
        }

        val expectedValues = listOf(1, 2, 3, 4)
        val actualValues = listOf("a", "b", "c", "d").map { cache[it] }
        assertEquals(expectedValues, actualValues)

        cache["e"] = 5
        assertEquals(5, cache["e"])
        assertEquals(null, cache["a"])
    }

    @Test
    fun putExistingKeyTest() {
        cache["a"] = 1
        assertEquals(1, cache["a"])
        cache["a"] = 2
        assertEquals(2, cache["a"])
    }

    @Test
    fun removeOldValuesTest() {
        val oldKv = mapOf(
            "a" to 1,
            "b" to 2,
            "c" to 3,
            "d" to 4
        )
        oldKv.forEach { (k, v) -> cache[k] = v }

        val newKv = mapOf(
            "a1" to 1,
            "b1" to 2,
            "c1" to 3,
            "d1" to 4
        )
        newKv.forEach { (k, v) -> cache[k] = v }

        newKv.forEach { (k, v) -> assertEquals(v, cache[k]) }
        oldKv.forEach { (k, _) -> assertEquals(null, cache[k]) }
    }
}