Stream.of(
VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4.75, 7, 0, 11.25, 11, 16), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 7, 4.75, 16, 11, 11.25), Block.makeCuboidShape(0, 7, 4.75, 16, 11, 11.25), IBooleanFunction.TABLE), IBooleanFunction.TABLE),
VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4.75, 7, -2, 11.25, 11, 14), Block.makeCuboidShape(0, 7, 2.75, 16, 11, 9.25), IBooleanFunction.TABLE_LEFT),
VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4.75, 7, 2, 11.25, 11, 18), Block.makeCuboidShape(4.75, 7, 2, 11.25, 11, 18), IBooleanFunction.TABLE_RIGHT),
VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4.75, 13, -2, 11.25, 14, 1), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(13, 13, 2.75, 16, 14, 9.25), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(13, 13, 2.75, 16, 14, 9.25), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4.75, 11, -2, 5.75, 13, 1), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(13, 11, 8.25, 16, 13, 9.25), Block.makeCuboidShape(9.75, 11, -9.5, 10.75, 13, -6.5), IBooleanFunction.TABLE_LEFT_TOP), IBooleanFunction.TABLE_LEFT_TOP), IBooleanFunction.TABLE_LEFT_TOP), IBooleanFunction.TABLE_LEFT_TOP), IBooleanFunction.TABLE_LEFT_TOP),
VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7, 0, 0, 9, 7, 16), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(3, 0, 2, 13, 7, 4), Block.makeCuboidShape(3, 0, 12, 13, 7, 14), IBooleanFunction.STAND), IBooleanFunction.STAND)
).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);});