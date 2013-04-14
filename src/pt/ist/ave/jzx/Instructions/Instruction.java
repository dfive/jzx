package pt.ist.ave.jzx.Instructions;

import java.util.HashMap;

import pt.ist.ave.jzx.Z80;

public abstract class Instruction {

	protected final short _opCode;
	
	protected static Z80 _cpu;
	
	private static HashMap<Integer, Integer> memoryCache = new HashMap<Integer, Integer>();

	public Instruction(short opCode) {
		_opCode = opCode;
	}
	
	public static void setCPU(Z80 cpu) {
		_cpu = cpu;
	}
	
	public static int readNextMemoryPosition(){
		int m_pc16 = _cpu.getM_pc16();
		if(!memoryCache.containsKey(m_pc16)){
			memoryCache.put(m_pc16,_cpu.getM_memory().read8(m_pc16));
		}
		return memoryCache.get(m_pc16);
	}
	
	public abstract void execute();
	
	public abstract int incTstates();
}
