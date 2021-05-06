package com.ryankshah.skyrimcraft.util;

import com.sun.javafx.geom.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Vector;
import java.util.function.Predicate;

public class RayTraceUtil
{
    public static Entity rayTrace(World world, PlayerEntity player, double range) {
        Vector3d pos = player.getPosition(0f);
        Vector3d cam1 = player.getLookAngle();
        Vector3d cam2 = cam1.add(cam1.x * range, cam1.y * range, cam1.z * range);
        AxisAlignedBB aabb = player.getBoundingBox().expandTowards(cam1.scale(range)).inflate(1.0F, 1.0F, 1.0F);
        RayTraceResult ray = findEntity(world, player, pos, cam2, aabb, null, range);

        if(ray != null) {
            if(ray.getType() == RayTraceResult.Type.ENTITY && ray instanceof EntityRayTraceResult) {
                EntityRayTraceResult ray2 = (EntityRayTraceResult) ray;
                Entity entity = ray2.getEntity();
                return entity;
            }
        }
        return null;
    }

    private static EntityRayTraceResult findEntity(World world, PlayerEntity player, Vector3d pos, Vector3d look, AxisAlignedBB aabb, Predicate<Entity> filter, double range) {
        for(Entity entity1 : world.getEntities(player, aabb, filter)) {
            AxisAlignedBB mob = entity1.getBoundingBox().inflate((double)1.0F);
            if(intersect(pos, look, mob, range)) {
                return new EntityRayTraceResult(entity1);
            }
        }
        return null;
    }

    private static boolean intersect(Vector3d pos, Vector3d look, AxisAlignedBB mob, double range) {
        Vector3d invDir = new Vector3d(1f / look.x, 1f / look.y, 1f / look.z);

        boolean signDirX = invDir.x < 0;
        boolean signDirY = invDir.y < 0;
        boolean signDirZ = invDir.z < 0;

        Vector3d max = new Vector3d(mob.maxX, mob.maxY, mob.maxZ);
        Vector3d min = new Vector3d(mob.minX, mob.minY, mob.minZ);

        Vector3d bbox = signDirX ? max : min;
        double tmin = (bbox.x - pos.x) * invDir.x;
        bbox = signDirX ? min : max;
        double tmax = (bbox.x - pos.x) * invDir.x;
        bbox = signDirY ? max : min;
        double tymin = (bbox.y - pos.y) * invDir.y;
        bbox = signDirY ? min : max;
        double tymax = (bbox.y - pos.y) * invDir.y;

        if ((tmin > tymax) || (tymin > tmax)) {
            return false;
        }
        if (tymin > tmin) {
            tmin = tymin;
        }
        if (tymax < tmax) {
            tmax = tymax;
        }

        bbox = signDirZ ? max : min;
        double tzmin = (bbox.z - pos.z) * invDir.z;
        bbox = signDirZ ? min : max;
        double tzmax = (bbox.z - pos.z) * invDir.z;

        if ((tmin > tzmax) || (tzmin > tmax)) {
            return false;
        }
        if (tzmin > tmin) {
            tmin = tzmin;
        }
        if (tzmax < tmax) {
            tmax = tzmax;
        }
        if ((tmin < range) && (tmax > 0)) {
            return true;
        }
        return false;
    }
}