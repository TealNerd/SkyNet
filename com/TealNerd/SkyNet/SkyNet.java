package com.TealNerd.SkyNet;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler; // used in 1.6.2
//import cpw.mods.fml.common.Mod.PreInit;    // used in 1.5.2
//import cpw.mods.fml.common.Mod.Init;       // used in 1.5.2
//import cpw.mods.fml.common.Mod.PostInit;   // used in 1.5.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="SkyNet", name="SkyNet", version="1.0.0")

public class SkyNet {

	static Minecraft mc = Minecraft.getMinecraft();
	static boolean isEnabled = true;
	public static KeyBinding toggle;
	static List<String> previousPlayerList = new ArrayList();
	
    // The instance of your mod that Forge uses.
    @Instance(value = "SkyNet")
    public static SkyNet instance;
    
    @SidedProxy(clientSide="com.TealNerd.SkyNet.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)  {
    	SkyNet.instance = this;
    	FMLCommonHandler.instance().bus().register(new KeyInputHandler());
    }
    
    
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
            proxy.registerListeners();
            TickHandler handler = new TickHandler();
            FMLCommonHandler.instance().bus().register(handler);
            toggle = new KeyBinding("Toggle SkyNet", Keyboard.KEY_I, "SkyNet");
            ClientRegistry.registerKeyBinding(toggle);           
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
    public static String filterChatColors(String s) {
    	return EnumChatFormatting.getTextWithoutFormattingCodes(s);
    }
    
    public static void onPlayerLeave(String player) {
    	mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "[SkyNet] "+ EnumChatFormatting.YELLOW + player + " left the game"));
    }
    
    public static void onPlayerJoin(String player) {
    	mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "[SkyNet] "+ EnumChatFormatting.YELLOW + player + " joined the game"));
    }
}
