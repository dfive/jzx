package pt.ist.ave.jzx.Instructions;

public class OR_N extends Instruction {

	public OR_N(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.or_a(_cpu.getM_memory().read8(_cpu.inc16pc()));
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
