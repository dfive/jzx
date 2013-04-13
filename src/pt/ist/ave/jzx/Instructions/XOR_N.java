package pt.ist.ave.jzx.Instructions;

public class XOR_N extends Instruction {

	public XOR_N(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.xor_a(_cpu.getM_memory().read8(_cpu.inc16pc()));
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
