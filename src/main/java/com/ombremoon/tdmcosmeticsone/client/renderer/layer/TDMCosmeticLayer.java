package com.ombremoon.tdmcosmeticsone.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ombremoon.tdmcosmeticsone.client.renderer.*;
import com.ombremoon.tdmcosmeticsone.CommonClass;
import com.ombremoon.tdmcosmeticsone.common.init.ItemInit;
import com.ombremoon.tdmcosmeticsone.common.object.item.TDMCosmeticItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class TDMCosmeticLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private final TDMCosmeticRenderer model;
    private final DisruptionBackpackRenderer disruptionBackpackLayer;
    private final VacuumBackpackRenderer vacuumBackpackLayer;

    public TDMCosmeticLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
        this.model = new TDMCosmeticRenderer();
        this.disruptionBackpackLayer = new DisruptionBackpackRenderer();
        this.vacuumBackpackLayer = new VacuumBackpackRenderer();
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        for (var type : TDMCosmeticItem.Type.values()) {
            this.renderCosmetic(pPoseStack, pBuffer, pLivingEntity, type, pPackedLight);
        }
    }

    private void renderCosmetic(PoseStack poseStack, MultiBufferSource bufferSource, T livingEntity, TDMCosmeticItem.Type type, int packedLight) {
        var result = CuriosApi.getCuriosInventory(livingEntity).orElse(null).findCurio(type.getName(), 0);
        if (result.isPresent() && result.get().slotContext().visible()) {
            ItemStack itemStack = result.get().stack();
            Item item = itemStack.getItem();
            if (item instanceof TDMCosmeticItem cosmeticItem) {
                this.getParentModel().copyPropertiesTo(model);
                model.prepForRender(livingEntity, itemStack, type.getPseudoSlot(), model);
                VertexConsumer consumer = bufferSource.getBuffer(RenderType.armorCutoutNoCull(model.getTextureLocation(cosmeticItem)));
                model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

                if (itemStack.is(ItemInit.DISRUPTION_BACKPACK.get())) {
                    disruptionBackpackLayer.prepForRender(livingEntity, itemStack, EquipmentSlot.CHEST, disruptionBackpackLayer);
                    VertexConsumer backpackConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(CommonClass.customLocation("textures/item/disruption_backpack_layer.png")));
                    disruptionBackpackLayer.renderToBuffer(poseStack, backpackConsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                }

                if (itemStack.is(ItemInit.VACUUM_BACKPACK.get())) {
                    vacuumBackpackLayer.prepForRender(livingEntity, itemStack, EquipmentSlot.CHEST, vacuumBackpackLayer);
                    VertexConsumer backpackConsumer = bufferSource.getBuffer(RenderType.armorCutoutNoCull(CommonClass.customLocation("textures/item/vacuum_backpack_layer.png")));
                    vacuumBackpackLayer.renderToBuffer(poseStack, backpackConsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }
}
