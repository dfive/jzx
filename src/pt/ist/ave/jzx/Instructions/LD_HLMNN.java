package pt.ist.ave.jzx.Instructions;

public class LD_HLMNN extends Instruction {

	public LD_HLMNN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.hl16(_cpu.getM_memory().read16(_cpu.getM_memory().read16(_cpu.getM_pc16())));
		_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
	}

	@Override
	public int incTstates() {
		return 16;
	}

}
