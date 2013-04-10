package pt.ist.ave.jzx.Instructions;

public class LDDEA extends Instruction {

	public LDDEA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.getM_memory().write8(_cpu.de16(), _cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
