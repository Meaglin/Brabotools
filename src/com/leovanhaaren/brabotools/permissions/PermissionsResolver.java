package com.leovanhaaren.brabotools.permissions;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.leovanhaaren.brabotools.permissions.ExPermissions;

public class PermissionsResolver {
    
    public static Permissions resolve(Plugin plugin) {
        PluginManager m = plugin.getServer().getPluginManager();
        
        Plugin permissionsPlugin = m.getPlugin("PermissionsEx");
        if(permissionsPlugin != null) {
            if(!permissionsPlugin.isEnabled()) m.enablePlugin(permissionsPlugin);
            return new ExPermissions(PermissionsEx.getPermissionManager());
        }
        
        permissionsPlugin = m.getPlugin("Permissions");
        if(permissionsPlugin != null) {
            if(!permissionsPlugin.isEnabled()) m.enablePlugin(permissionsPlugin);
            return new NijiPermissions((com.nijikokun.bukkit.Permissions.Permissions)permissionsPlugin);
        }
        
        return new BukkitPermissions(plugin);
    }
}
