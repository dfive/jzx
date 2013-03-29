package pt.ist.ave.jzx.Instructions;

import pt.ist.ave.jzx.Z80;

public abstract class Instruction {

	protected final short _opCode;
	
	protected static Z80 _cpu;

	public Instruction(short opCode) {
		_opCode = opCode;
	}
	
	public void setCPU(Z80 cpu) {
		_cpu = cpu;
	}
	
	public abstract void execute();
	
	public abstract int incTstates();
}
