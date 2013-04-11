package pt.ist.ave.jzx.Instructions;

public class ADD_HLHL extends Instruction {

	public ADD_HLHL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.add_hl(_cpu.hl16());
	}

	@Override
	public int incTstates() {
		return 11;
	}

}
