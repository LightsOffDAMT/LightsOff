package ru.lightsoff.database.client.entities.injected

import ru.lightsoff.database.Entities.ItemInGame
import ru.lightsoff.database.Entities.ItemInStorage

class ItemInGameInjected: ItemInGame() {
    var item: ItemInStorage = ItemInStorage()
        private set

    fun withItem(item: ItemInStorage): ItemInGame{
        this.item = item
        return this
    }
}