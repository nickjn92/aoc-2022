package day13

import PartSolver
import day13.Packet.IntPacket
import day13.Packet.ListPacket

class Part2 : PartSolver<Int> {

    override fun solve(input: List<String>, isTest: Boolean): Int {
        val dividerA = ListPacket(listOf(IntPacket(2)))
        val dividerB = ListPacket(listOf(IntPacket(6)))

        val packets = input.toPackets()
            .toMutableList()

        packets.add(dividerA)
        packets.add(dividerB)
        packets.sort()

        val idxA = packets.indexOf(dividerA) + 1
        val idxB = packets.indexOf(dividerB) + 1

        /*
         * Less computational answer would just to be iterate through all packets and
         * calculate idxA as number of packets being < dividerA and likewise for b
         */
        return idxA * idxB
    }
}
