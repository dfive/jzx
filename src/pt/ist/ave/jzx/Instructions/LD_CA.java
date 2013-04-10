package pt.ist.ave.jzx.Instructions;

public class LD_CA extends Instruction {

	public LD_CA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_c8(_cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
