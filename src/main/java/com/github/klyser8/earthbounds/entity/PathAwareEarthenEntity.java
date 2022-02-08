package com.github.klyser8.earthbounds.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class PathAwareEarthenEntity extends PathAwareEntity implements Earthen {

    private final AnimationFactory factory;

    private static final TrackedData<Integer> LAST_DAMAGER_ID = DataTracker.registerData(PathAwareEarthenEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    //Holds ID of the current animation flag.
    private static final TrackedData<Byte> ANIMATION_STATE = DataTracker.registerData(PathAwareEarthenEntity.class,
            TrackedDataHandlerRegistry.BYTE);

    //Default state for any entity
    protected static final byte DEFAULT_STATE = 0;

    protected PathAwareEarthenEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.factory = new AnimationFactory(this);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(LAST_DAMAGER_ID, getId());
        dataTracker.startTracking(ANIMATION_STATE, (byte) 0);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return super.isInvulnerableTo(damageSource)
                || damageSource.equals(DamageSource.ON_FIRE)
                || damageSource.equals(DamageSource.IN_FIRE);
    }

    @Override
    public EntityGroup getGroup() {
        return EarthboundEntityGroup.EARTHEN;
    }

    @Override
    public Entity getLastDamager() {
        return world.getEntityById(dataTracker.get(LAST_DAMAGER_ID));
    }

    @Override
    public void setLastDamager(Entity entity) {
        dataTracker.set(LAST_DAMAGER_ID, entity.getId());
    }

    public int getAnimationState() {
        return dataTracker.get(ANIMATION_STATE);
    }

    public void setAnimationState(byte id) {
        dataTracker.set(ANIMATION_STATE, id);
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    /**
     * Allows the entity to dash in the specified direction.
     * All earthen entities use it to circumvent a minecraft pathfinding bug.
     *
     * @param direction the direction of the dash
     * @param hMultiplier the horizontal strength of the dash
     * @param vMultiplier the vertical strength of the dash
     */
    protected void dash(Vec3d direction, float hMultiplier, float vMultiplier) {
        addVelocity(direction.x * hMultiplier, direction.y * vMultiplier, direction.z * hMultiplier);
    }


}
