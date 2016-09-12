package com.biggestnerd.skynet;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
 
@Mod(modid="skynet", name="SkyNet", version="1.1.0")
public class SkyNet {
	
	Minecraft mc = Minecraft.getMinecraft();
    boolean isEnabled = true;
    KeyBinding toggle;
    List<String> previousPlayerList = new ArrayList();
   
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)  {
        MinecraftForge.EVENT_BUS.register(this);
        toggle = new KeyBinding("Toggle SkyNet", Keyboard.KEY_I, "SkyNet");
        ClientRegistry.registerKeyBinding(toggle);    
    }
   
    public String filterChatColors(String s) {
        return TextFormatting.getTextWithoutFormattingCodes(s);
    }
   
    public void onPlayerLeave(String player) {
        mc.thePlayer.addChatMessage(new TextComponentString(TextFormatting.DARK_AQUA + "[SkyNet] "+ TextFormatting.GRAY + player + " left the game"));
    }
   
    public void onPlayerJoin(String player) {
        mc.thePlayer.addChatMessage(new TextComponentString(TextFormatting.DARK_AQUA + "[SkyNet] "+ TextFormatting.GRAY + player + " joined the game"));
    }
 
    @SubscribeEvent
    public void onTick(ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            if(isEnabled) {
                if(mc.theWorld != null) {      
                ArrayList<String> playerList = new ArrayList();
                Collection<NetworkPlayerInfo> players = mc.getConnection().getPlayerInfoMap();
                for(NetworkPlayerInfo info : players) {
                    playerList.add(filterChatColors(info.getGameProfile().getName()));
                }
                ArrayList<String> temp = (ArrayList)playerList.clone();
                playerList.removeAll(previousPlayerList);
                previousPlayerList.removeAll(temp);
                for(String player : previousPlayerList) {
                    onPlayerLeave(player);
                }
                for(String player : playerList) {
                    onPlayerJoin(player);
                }
                previousPlayerList = temp;
                }
            }
        }
    }
   
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(toggle.isPressed()){
            if(!isEnabled){
            mc.thePlayer.addChatMessage(new TextComponentString(TextFormatting.DARK_AQUA + "[SkyNet] "+ TextFormatting.GRAY + "SkyNet Enabled"));
            isEnabled = true;
            }else if(isEnabled){
            mc.thePlayer.addChatMessage(new TextComponentString(TextFormatting.DARK_AQUA + "[SkyNet] "+ TextFormatting.GRAY + "SkyNet Disabled"));
            isEnabled = false;
            }
        }              
    }
}
