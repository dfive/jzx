package pt.ist.ave.jzx.Instructions;

import java.util.HashMap;

import pt.ist.ave.jzx.Z80;

public abstract class Instruction {

	protected final short _opCode;
	
	protected static Z80 _cpu;
	
	private static HashMap<Integer, Integer> memoryCache8 = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Integer> memoryCache16 = new HashMap<Integer, Integer>();

	
	public Instruction(short opCode) {
		_opCode = opCode;
	}
	
	public static void setCPU(Z80 cpu) {
		_cpu = cpu;
	}
	
	public static int readNextMemoryPosition8(){
		int m_pc16 = _cpu.getM_pc16();
		if(!memoryCache8.containsKey(m_pc16)){
			memoryCache8.put(m_pc16,_cpu.getM_memory().read8(m_pc16));
		}
		return memoryCache8.get(m_pc16);
	}
	
	public static int readNextMemoryPosition16(){
		int m_pc16 = _cpu.getM_pc16();
		if(!memoryCache16.containsKey(m_pc16)){
			memoryCache16.put(m_pc16,_cpu.getM_memory().read16(m_pc16));
		}
		return memoryCache16.get(m_pc16);
	}
	
	public static void cleanCaches(){
		memoryCache8.clear();
		memoryCache16.clear();
	}
	
	public abstract void execute();
	
	public abstract int incTstates();
}
