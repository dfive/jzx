package pt.ist.ave.jzx.instructions;

public class NOP extends Instruction {

	public NOP(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		// DO NOTHING		
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
