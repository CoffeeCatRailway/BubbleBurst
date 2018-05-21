package coffeecatteam.bubbleburst.init.entity;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBomb extends EntityThrowable {

	public EntityBomb(World world) {
		super(world);
	}

	public EntityBomb(World world, EntityPlayer playerEntity) {
		super(world, playerEntity);
	}

	public EntityBomb(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.world.isRemote) {
			boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
			this.world.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, flag);
			
			this.world.setEntityState(this, (byte) 3);
			this.setDead();
		}
	}
}
