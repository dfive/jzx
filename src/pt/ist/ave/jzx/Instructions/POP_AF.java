package pt.ist.ave.jzx.Instructions;

public class POP_AF extends Instruction {

	public POP_AF(short opCode) { 
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.af16(_cpu.pop16());
		_cpu.retrieveFlags();
	}

	@Override
	public int incTstates() {
		return 10;
	}

}
