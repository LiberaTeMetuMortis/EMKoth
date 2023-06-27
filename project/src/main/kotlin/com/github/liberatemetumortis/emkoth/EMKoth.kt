package com.github.liberatemetumortis.emkoth

import org.bukkit.plugin.java.JavaPlugin

class EMKoth : JavaPlugin() {
    companion object {
        lateinit var instance: EMKoth
    }
    override fun onEnable() {
        instance = this
        saveDefaultConfig()
        reloadConfig()
        Koth.getKothsFromConfig(config.getConfigurationSection("koths")!!)
        Koth.koths.forEach(Koth::start)
    }

    override fun onDisable() {
        TODO("Plugin shutdown logic")
    }
}
