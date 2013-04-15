package pt.ist.ave.jzx;

import java.util.HashMap;

import pt.ist.ave.jzx.Instructions.Instruction;


public class InstructionsCache {
	
	/*
	 * maps MemoryPosition |-> Decoded Instruction
	 */
	private static HashMap<Integer, Instruction> instructions;
	
	/*
	 * the maximum number of instructions that the cache keeps in memory
	 */
	private final int maxCapacity;
	
	public InstructionsCache(int maxCapacity){
		this.maxCapacity = maxCapacity;
		this.instructions = new HashMap<Integer, Instruction>();
	}
	
	/*
	 * Adds an instruction to the cache if the max size of the cache has not been reached
	 * otherwise it uses some policy of substitution.
	 */
	public void addInstruction(Integer memoryPosition, Instruction instruction){
			instructions.put(memoryPosition, instruction);
	}
	
	/*
	 * true if the given memory position is in cache
	 */
	public boolean isHit(Integer memoryPosition){
		return instructions.containsKey(memoryPosition);
	}
	
	/*
	 * gets a decoded memory position from the cache
	 */
	public Instruction get(Integer memoryPosition){
		return instructions.get(memoryPosition);
	}
	
	public static void clearCache(){
		if(instructions!=null){
			instructions.clear();
		}
	}
}
