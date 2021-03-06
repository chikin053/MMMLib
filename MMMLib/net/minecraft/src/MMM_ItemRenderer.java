package net.minecraft.src;

import java.lang.reflect.Method;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import net.minecraft.client.Minecraft;

public class MMM_ItemRenderer extends ItemRenderer implements MMM_IItemRenderer {

	// プライベート変数を使えるように
	public Minecraft mc;
	public ItemStack itemToRender;
	public float equippedProgress;
	public float prevEquippedProgress;


	public MMM_ItemRenderer(Minecraft minecraft) {
		super(minecraft);
		
		mc = minecraft;
	}

	@Override
	public Minecraft getMC() {
		return mc;
	}

	@Override
	public ItemStack getItemToRender() {
		return itemToRender;
	}

	@Override
	public float getEquippedProgress() {
		return equippedProgress;
	}

	@Override
	public float getPrevEquippedProgress() {
		return prevEquippedProgress;
	}

	@Override
	public void renderItem(EntityLiving entityliving, ItemStack itemstack, int i) {
		Item litem = itemstack.getItem();
		if (MMM_ItemRenderManager.isEXRender(litem)) {
			MMM_ItemRenderManager lii = MMM_ItemRenderManager.getEXRender(litem);
			String ltex = lii.getRenderTexture();
			if (ltex != null) {
				this.mc.renderEngine.bindTexture(ltex);
			}
			GL11.glPushMatrix();
			boolean lflag = lii.renderItem(entityliving, itemstack, i);
			GL11.glPopMatrix();
			if (lflag) {
				if (itemstack != null && itemstack.hasEffect() && i == 0) {
					GL11.glDepthFunc(GL11.GL_EQUAL);
					GL11.glDisable(GL11.GL_LIGHTING);
					this.mc.renderEngine.bindTexture("%blur%/misc/glint.png");
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
					float var14 = 0.76F;
					GL11.glColor4f(0.5F * var14, 0.25F * var14, 0.8F * var14, 1.0F);
					float var15 = 0.125F;
					
					GL11.glPushMatrix();
					GL11.glMatrixMode(GL11.GL_TEXTURE);
					GL11.glLoadIdentity();
					GL11.glScalef(var15, var15, var15);
					float var16 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
					GL11.glTranslatef(var16, 0.0F, 0.0F);
					GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					lii.renderItem(entityliving, itemstack, 0);
//					renderItemIn2D(var6, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
					GL11.glPopMatrix();
					
					GL11.glPushMatrix();
					GL11.glMatrixMode(GL11.GL_TEXTURE);
					GL11.glLoadIdentity();
					GL11.glScalef(var15, var15, var15);
					var16 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
					GL11.glTranslatef(-var16, 0.0F, 0.0F);
					GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					lii.renderItem(entityliving, itemstack, 0);
//					renderItemIn2D(var6, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
					GL11.glPopMatrix();
					
					GL11.glMatrixMode(GL11.GL_TEXTURE);
					GL11.glLoadIdentity();
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glDepthFunc(GL11.GL_LEQUAL);
				}
				return;
			}
		}
		super.renderItem(entityliving, itemstack, i);
	}

	@Override
	public void renderItemInFirstPerson(float f) {
		itemToRender = null;
		equippedProgress = 0.0F;
		prevEquippedProgress = 0.0F;
		
		try {
			// ローカル変数を確保
			itemToRender = (ItemStack)ModLoader.getPrivateValue(ItemRenderer.class, this, 1);
			equippedProgress = (Float)ModLoader.getPrivateValue(ItemRenderer.class, this, 2);
			prevEquippedProgress = (Float)ModLoader.getPrivateValue(ItemRenderer.class, this, 3);
		} catch (Exception e) {
		}
		
		if (itemToRender != null) {
			Item litem = itemToRender.getItem();
			if (MMM_ItemRenderManager.isEXRender(litem)) {
				if (MMM_ItemRenderManager.getEXRender(litem).renderItemInFirstPerson(f, this)) {
					return;
				}
			}
		}
		
		super.renderItemInFirstPerson(f);
	}


}
