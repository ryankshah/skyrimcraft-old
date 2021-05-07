Stream.of(
Block.makeCuboidShape(1, 0, 1, 15, 2, 13),
Block.makeCuboidShape(0, 0, 9, 12, 2, 15),
Block.makeCuboidShape(12, 0, 13, 13, 3, 14),
Block.makeCuboidShape(12, 0, 13, 15, 1, 15),
Block.makeCuboidShape(11, 2, 3, 14, 3, 10),
Block.makeCuboidShape(2, 2, 3, 11, 5, 12),
Block.makeCuboidShape(7, 5, 6, 10, 6, 9),
Block.makeCuboidShape(6, 5, 4, 7, 6, 9),
Block.makeCuboidShape(3, 5, 4, 6, 6, 7),
Block.makeCuboidShape(15, 0, 11, 16, 1, 14)
).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);});