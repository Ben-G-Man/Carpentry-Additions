package io.github.bengman.carpentryadditions.registry;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import io.github.bengman.carpentryadditions.menu.ResawMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES,
            CarpentryAdditions.MODID);

    public static final RegistryObject<MenuType<ResawMenu>> RESAW_MENU = MENUS.register("resaw_menu",
            () -> IForgeMenuType.create((containerId, inventory, buf) -> new ResawMenu(containerId, inventory, buf)));
}