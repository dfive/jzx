package pt.ist.ave.jzx.instructions;

public class PUSH_DE extends Instruction {

	public PUSH_DE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.push(_cpu.de16());
	}

	@Override
	public int incTstates() {
		return 11;
	}

}
