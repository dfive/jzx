package pt.ist.ave.jzx.Instructions;

public class InstructionsSubset extends Instruction {

	public InstructionsSubset(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.decodeED(_cpu.mone8());
	}

	@Override
	public int incTstates() {
		return 0;
	}

}
