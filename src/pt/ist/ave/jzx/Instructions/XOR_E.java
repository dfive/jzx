package pt.ist.ave.jzx.Instructions;

public class XOR_E extends Instruction {

	public XOR_E(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.xor_a(_cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
