package pt.ist.ave.jzx.Instructions;

public class POP_DE extends Instruction {

	public POP_DE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.de16(_cpu.pop16());
	}

	@Override
	public int incTstates() {
		return 10;
	}

}
