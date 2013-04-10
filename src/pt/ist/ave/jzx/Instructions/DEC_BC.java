package pt.ist.ave.jzx.Instructions;

public class DEC_BC extends Instruction {

	public DEC_BC(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.dec16bc();
	}

	@Override
	public int incTstates() {
		return 6;
	}

}
