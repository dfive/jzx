package pt.ist.ave.jzx.Instructions;

public class POPAF extends Instruction {

	public POPAF(short opCode) { 
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
