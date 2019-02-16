package ru.lightsoff.database.client.entities.injected

import ru.lightsoff.database.Entities.ItemInGame
import ru.lightsoff.database.Entities.ItemInStorage

class ItemInGameInjected(itemInStorage: ItemInStorage, itemInGame: ItemInGame): ItemInGame() {
    var itemInStorage: ItemInStorage = ItemInStorage()
        private set

    init {
        this.id = itemInGame.id
        this.itemID = itemInGame.itemID
        this.position = itemInGame.position
        this.itemInStorage = itemInStorage;
    }
}