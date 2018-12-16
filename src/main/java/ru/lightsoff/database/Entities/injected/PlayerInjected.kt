package ru.lightsoff.database.client.entities.injected

import ru.lightsoff.database.Entities.Player
import ru.lightsoff.database.Entities.User

class PlayerInjected(player: Player, user: User) : Player() {

    var user: User? = null
        private set

    init {
        this.id = player.id
        this.name = player.name
        this.inventory = player.inventory
        this.position = player.position
        this.stats = player.stats
        this.userID = player.userID
        this.user = user
    }

    fun withUser(user: User?): PlayerInjected{
        this.user = user
        return this
    }
}