package com.example.horrormod.client;

import com.example.horrormod.HorrorMod;
import com.example.horrormod.entity.GhostEntity;
import com.example.horrormod.sanity.SanityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorrorMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientJumpScare {
    private static final SoundEvent JUMPSCARE = new SoundEvent(new ResourceLocation(HorrorMod.MODID, "jumpscare"));
    private static int cooldown = 0;
    private static int visualTimer = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        LocalPlayer player = mc.player;

        int sanity = SanityUtils.getSanity(player);
        if (cooldown > 0) { cooldown--; return; }

        boolean low = sanity <= 30;
        if (!low) return;

        // If any ghost entity is nearby (within 8 blocks) or random chance, play jumpscare sound
        boolean ghostNear = !player.level.getEntitiesOfClass(GhostEntity.class, player.getBoundingBox().inflate(8.0D)).isEmpty();
        boolean randomTrigger = player.level.random.nextInt(1200) == 0; // rare random event

        if (ghostNear || randomTrigger) {
            player.playSound(JUMPSCARE, SoundSource.AMBIENT, 1.0F, 1.0F);
            SanityUtils.changeSanity(player, -10); // jump-scare reduces sanity further
            cooldown = 20 * 10; // 10s cooldown
            // trigger visual if enabled in config
            if (com.example.horrormod.config.HorrorConfig.ENABLE_VISUAL_JUMPSCARE.get()) {
                visualTimer = 40; // show for ~2 seconds
            }
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(net.minecraftforge.client.gui.RenderGameOverlayEvent.Post event) {
        if (event.getType() != net.minecraftforge.client.gui.RenderGameOverlayEvent.ElementType.ALL) return;
        if (visualTimer <= 0) return;
        Minecraft mc = Minecraft.getInstance();
        com.mojang.blaze3d.vertex.PoseStack ms = event.getMatrixStack();
        net.minecraft.resources.ResourceLocation tex = new net.minecraft.resources.ResourceLocation(HorrorMod.MODID, "textures/gui/duck.png");
        mc.getTextureManager().bind(tex);
        int w = mc.getWindow().getGuiScaledWidth();
        int h = mc.getWindow().getGuiScaledHeight();
        int imgW = 128;
        int imgH = 128;
        net.minecraft.client.gui.GuiComponent.blit(ms, w / 2 - imgW / 2, h / 2 - imgH / 2, 0, 0, imgW, imgH, imgW, imgH);
        visualTimer--;
    }
}
