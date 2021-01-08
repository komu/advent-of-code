package aoc2020

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LobbyLayoutTest {

    private val testInput = """
            sesenwnenenewseeswwswswwnenewsewsw
            neeenesenwnwwswnenewnwwsewnenwseswesw
            seswneswswsenwwnwse
            nwnwneseeswswnenewneswwnewseswneseene
            swweswneswnenwsewnwneneseenw
            eesenwseswswnenwswnwnwsewwnwsene
            sewnenenenesenwsewnenwwwse
            wenwwweseeeweswwwnwwe
            wsweesenenewnwwnwsenewsenwwsesesenwne
            neeswseenwwswnwswswnw
            nenwswwsewswnenenewsenwsenwnesesenew
            enewnwewneswsewnwswenweswnenwsenwsw
            sweneswneswneneenwnewenewwneswswnese
            swwesenesewenwneswnwwneseswwne
            enesenwswwswneneswsenwnewswseenwsese
            wnwnesenesenenwwnenwsewesewsesesew
            nenewswnwewswnenesenwnesewesw
            eneswnwswnwsenenwnwnwwseeswneewsenese
            neswnwewnwnwseenwseesewsenwsweewe
            wseweeenwnesenwwwswnew
        """.trimIndent()

    @Test
    fun `example 1`() {
        assertEquals(10, lobbyLayout1(testInput))
    }

    @Test
    fun `example 2`() {
        assertEquals(2208, lobbyLayout2(testInput))
    }

    @Test
    fun `part 1`() {
        assertEquals(523, lobbyLayout1(readTestInput("/2020/LobbyLayout.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(4225, lobbyLayout2(readTestInput("/2020/LobbyLayout.txt")))
    }
}
